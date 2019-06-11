package hello;


import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Model{
	
	
	
	
	
ObjectContainer locals = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/locals.db4o");
	
	
	public boolean addInstituicao(Instituicao instituicao){
		//if(isUserAvailable(instituicao.getName())){
		
		Query query = locals.query();
		query.constrain(Instituicao.class);
	    
	    
	    locals.store(instituicao);
	    locals.commit();
	    
	    Query query2 = locals.query();
	    query2.constrain(Instituicao.class);
	    /*
	    ObjectSet<Instituicao> allStudents = query.execute();
	    
	    for(Instituicao student:allStudents){
	    	System.out.println(student.getName());
	    }*/
		return true;
	//}
	
	//return false;
		
	}
	
	public boolean isUserAvailable(String username){
		Query query = locals.query();
		query.constrain(Instituicao.class);
	    ObjectSet<Instituicao> allStudents = query.execute();
	    
	    for(Instituicao student:allStudents){
	    	if(student.getName().equals(username)) return false;
	    }
	    
	    return true;
	}
	
}
