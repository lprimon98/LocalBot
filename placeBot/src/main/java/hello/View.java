package hello;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class View implements Observer {

	TelegramBot bot = TelegramBotAdapter.build("765011207:AAFna03HS5RDALawF1TBrRE8UpkUcTwIqI8");

	// Object that receives messages
	GetUpdatesResponse updatesResponse;
	// Object that send responses
	SendResponse sendResponse;
	// Object that manage chat actions like "typing action"
	BaseResponse baseResponse;

	int queuesIndex = 0;

	ControllerSearch controllerSearch; // Strategy Pattern -- connection View -> Controller

	String categoria = "";

	boolean searchBehaviour = false;

	private Model model;

	public View(Model model) {
		this.model = model;
	}

	public void setControllerSearch(ControllerSearch controllerSearch) { // Strategy Pattern
		this.controllerSearch = controllerSearch;
	}

	ControllerSearchInstituicao controllerSearchInstituicao = new ControllerSearchInstituicao(model, this);

	public void receiveUsersMessages() {
		List<String> locais = new ArrayList<>();
		locais.add("Restaurante");
		locais.add("Hamburgueria");
		locais.add("Cinema");
		locais.add("Mall");
		locais.add("Mercado");
		locais.add("Pub");
		locais.add("Teatro");
		locais.add("Hotel");
		locais.add("Escola");
		// infinity loop
		while (true) {

			// taking the Queue of Messages
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(queuesIndex));

			// Queue of messages
			List<Update> updates = updatesResponse.updates();

			// taking each message in the Queue

			try {
				ServerSocket s = new ServerSocket(3000);

				for (Update update : updates) {

					// updating queue's index
					queuesIndex = update.updateId() + 1;

					if (this.searchBehaviour == true) {
						this.callController(update);

					}
					if (update.message().location() != null) {
						String latlon = update.message().location().latitude() + ","
								+ update.message().location().longitude();

						try {
							controllerSearchInstituicao.searchAPI(latlon, categoria);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						categoria = "";
					}
					Socket clientSocket = s.accept();
					String json = update.message().text();
					//Coloca a mensagem do usuario no servidor local
					Server.serverConnection(s, clientSocket, json);
					
					//Acessa a resposta do express em outro servidor local
					String msg = Client.serverExpress();
					
					categoria = msg; //Split para pegar o segundo valor do json
					String response = msg; //Pegar a primeira parte
					setControllerSearch(new ControllerSearchInstituicao(model, this));
					sendResponse = bot
							.execute(new SendMessage(update.message().chat().id(), response));
					
					if (locais.contains(categoria)) {
						KeyboardButton button = new KeyboardButton("Enviar Localizaçao");
						KeyboardButton[][] listButtons = { { button.requestLocation(true) } };
						ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(listButtons).resizeKeyboard(true)
								.oneTimeKeyboard(true);
						sendResponse = bot
								.execute(new SendMessage(update.message().chat().id(), "Me envie sua localização")
										.replyMarkup(reply));

					}
					this.searchBehaviour = true;

				}

			} catch (IOException e) {

			}
		}

	}

	public void callController(Update update) {
		this.controllerSearch.search(update);
	}

	public void update(long chatId, String studentsData) {
		sendResponse = bot.execute(new SendMessage(chatId, studentsData));
		this.searchBehaviour = false;
	}

	public void sendTypingMessage(Update update) {
		baseResponse = bot.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
	}

	private List<Observer> observers = new LinkedList<Observer>();

	private static Model uniqueInstance;

	// ObjectContainer locals =
	// Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/locals.db4o");

	public static Model getInstance() {

		if (uniqueInstance == null) {
			uniqueInstance = new Model();
		}
		return uniqueInstance;
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

}