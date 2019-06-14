import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.ainslec.picocog.PicoWriter;
import org.bson.Document;
//import org.ainslec.picocog.PicoWriter;
import org.bson.RawBsonDocument;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;

public class Code_gen {

    private MongoDataBase db;
    private HashMap<String, String> web_var_TO_var;


    Object obj=null;

    public Code_gen(MongoDataBase db)
    {
        this.db=db;
        this.web_var_TO_var = new HashMap<String, String>();
    }


    public String get_dostuff()
    {
        String output="";
        String way = "";
        MongoCollection<Right> rights= db.getRights();
        Bson d3 = new Document();
        FindIterable<Right> findIterable_r = rights.find(d3);
        MongoCursor<Right> iter_r = findIterable_r.iterator();
        while(iter_r.hasNext())
        {
            Right r = iter_r.next();
            String output1 = "";
            for(String s:r.getFormulas())
            {
                List<String> parts = Arrays.asList(s.split(" "));
                List<String> fixed_parts= parts.stream().map(name->db.get_var(name)).collect(Collectors.toList());
                way="";
                for(int i=0; i<fixed_parts.size(); i++)
                {
                    if(fixed_parts.get(i).equals(".equals("))
                    {
                        way+=fixed_parts.get(i)+fixed_parts.get(++i) +")";
                    }
                    else
                    {
                        way+=" "+fixed_parts.get(i);
                    }
                }
                output1 += make_if_statment(way,r);
            }
            output += surround_with_if_right_achived(output1,r.getRight_name());
        }
        return output;
    }
//			}
//		}
//		for(Right r:rights){
//			for(String s:r.getFormulas())
//			{
//				List<String> parts = Arrays.asList(s.split(" "));
//				List<String> fixed_parts= parts.stream().map(name->db.get_var(name)).collect(Collectors.toList());
//				way="";
//				for(int i=0; i<fixed_parts.size(); i++)
//				{
//					if(fixed_parts.get(i).equals(".equals("))
//					{
//						way+=fixed_parts.get(i)+fixed_parts.get(++i) +")";
//					}
//					else
//					{
//					way+=" "+fixed_parts.get(i);
//					}
//				}
//				output+=make_if_statment(way);
//			}
//			output = surround_with_if_right_achived(output,r.getId());
//		}
//		return output;
//	}


    public String get_code()
            throws IOException
    {

        PicoWriter outer=new PicoWriter();
        outer.writeln("import java.util.LinkedList;");
        outer.writeln("import java.util.List;");
        outer.writeln("import org.ainslec.picocog.PicoWriter;");
        outer.writeln("import org.bson.Document;");
        outer.writeln("import org.ainslec.picocog.PicoWriter;");
        outer.writeln("import org.bson.RawBsonDocument;");
        outer.writeln("import org.bson.conversions.Bson;");
        outer.writeln("import com.mongodb.client.FindIterable;");
        outer.writeln("import com.mongodb.client.MongoCollection;");
        outer.writeln("import com.mongodb.client.MongoCursor;");
        outer.writeln("import com.mongodb.client.model.Projections;");
        outer.writeln("public class Code implements DoStuff{");
        outer.writeln("MongoDataBase db =MongoDataBase.getInstance();");
        outer.writeln("public String doStuff()"
                + "{\n"
                + get_dostuff()
                +"String max_var = db.get_max_var();\n"
                +"Bson filter = new Document(\"id\",db.getApp_user().getId());\n"
                +"\t\tdb.getApp_users().replaceOne(filter,db.getApp_user());\n"
                + "if(!max_var.equals(\"finish\")){\n"
                + "return max_var.substring(3);\n"
                + "}\n"
                + "return max_var;\n"
                + "}\n");
        outer.writeln("}");
        return(outer.toString());

    }

    public String generator()
            throws IOException {

//		if(obj!=null)
//		{
//			if (obj instanceof DoStuff) {
//				// Cast to the DoStuff interface
//				DoStuff stuffToDo = (DoStuff)obj;
//				// Run it baby
//				return stuffToDo.doStuff();
//			}
//		}
        String fileName = "./target/classes/Code.java";//"./Code.java"
        File sourceFile=null;
        try
        {
            String str= get_code();

            Path path = Paths.get("./target/classes/Code.java");//"./Code.java"
            if(!Files.exists(path))
                Files.createFile(path);
            sourceFile = new File(fileName);
            Writer writer = null;
            try {
                writer = new FileWriter(sourceFile);
                writer.write(str);
                writer.flush();
            }
            finally{
                writer.close();
            }

        }
        catch(IOException e)
        {
            System.out.println("not good!");
        }
        try {
            BufferedReader file = new BufferedReader(new FileReader(sourceFile));
            String s = file.readLine();
            while (s != null){
                //System.out.println(s);
                s = file.readLine();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try{


            /** Compilation Requirements *********************************************************************************************/
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);

            // This sets up the class path that the compiler will use.
            // I've added the .jar file that contains the DoStuff interface within in it...
            List<String> optionList = new ArrayList<String>();
            optionList.add("-classpath");
            optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");

            Iterable<? extends JavaFileObject> compilationUnit
                    = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fileName));
            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    optionList,
                    null,
                    compilationUnit);
            /********************************************************************************************* Compilation Requirements **/
            if (task.call()) {
                /** Load and execute *************************************************************************************************/
                //System.out.println("Yipe");
                // Create a new custom class loader, pointing to the directory that contains the compiled
                // classes, this should point to the top of the package structure!
                URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
                // Load the class from the classloader by name....
                Class<?> loadedClass = classLoader.loadClass("Code");
                // Create a new instance...
                obj = loadedClass.newInstance();
                // Santity check
                classLoader.close();
                fileManager.close();
                if (obj instanceof DoStuff) {
                    // Cast to the DoStuff interface
                    DoStuff stuffToDo = (DoStuff)obj;
                    // Run it baby
                    return stuffToDo.doStuff();
                }
                /************************************************************************************************* Load and execute **/
            } else {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
                    System.out.format("Error on line %d in %s%n",
                            diagnostic.getLineNumber(),
                            diagnostic.getSource().toUri());
                }
            }


        } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exp) {
            exp.printStackTrace();
        }
        return null;
    }


    public String surround_with_if_right_achived(String output, String right_name)
    {
        return "if(!db.is_user_achived_right(\""+right_name+"\")){\n" + output + "\n}";
    }

    public String make_if_statment(String stat,Right r)
    {
        return "try{"+
                "if ("+stat+")\n"+
                "{\n\t"+
                "db.getApp_user().add_right(\""+r.getRight_name()+"\");\n"+
                "}\n"+
                "}\n"+
                "catch(NumberFormatException e){}\n";
    }

//	public String add_variables()
//	{
//
//		List<String> vars= db.get_vars();
//		List<Boolean> types= db.get_types();
//		String output="";
//		for(int i=0;i<vars.size();i++)
//		{
//			output+=add_var(types.get(i), vars.get(i));
//		}
//		return output;
//	}
//
//
//	public String add_var(boolean is_String,String name)
//	{
//		String output="Integer "+ name+ " = null;\n";
//		if(is_String)
//			output = "String "+ name + " = null\n";
//		return  output;
//	}
//
//	public static String get_code()
//			throws IOException
//	{
//		Code_gen gen=new Code_gen();
//		PicoWriter outer=new PicoWriter();
//		outer.writeln("import java.util.LinkedList;");
//		outer.writeln("import java.util.List;");
//		outer.writeln("public class Code implements DoStuff{");
//		outer.writeln("DataBase db=DataBase.getInstance();");
//		outer.writeln("public List<String> doStuff()"
//				+ "{\n"
//				//+ "db.set_ivar(\"num_of_children\",3);\n"
//				//+ "db.set_ivar(\"income\",new Integer(3000));\n"
//				//+ "db.set_ivar(\"income_partner\",new Integer(3000));\n"
//				+"List rights=new LinkedList<String>();\n"
//				+"List question= new LinkedList<String>();\n"
//				+"rights.add(\"finish\");\n"
//				+"question.add(\"next\");\n"
//				+ gen.get_dostuff()
//				+"String max_var = db.get_max_var();\n"
//				+ "if(max_var.equals(\"finish\")){\n"
//				+ "return rights;\n"
//				+ "}\n"
//				+ "question.add(max_var);\n"
//				+"db.clear_rates();\n"
//				+"return question;\n "
//				+ "}\n");
//		outer.writeln("}");
//		return(outer.toString());
//
//	}
//
//	public List<String> generator()
//			throws IOException {
//
//		if(obj!=null)
//		{
//			if (obj instanceof DoStuff) {
//				// Cast to the DoStuff interface
//				DoStuff stuffToDo = (DoStuff)obj;
//				// Run it baby
//				return stuffToDo.doStuff();
//			}
//		}
//		String fileName = "./bin/Code.java";
//		File sourceFile=null;
//		try
//		{
//			String str=Code_gen.get_code();
//
//			Path path = Paths.get("./bin/Code.java");
//			if(!Files.exists(path))
//				Files.createFile(path);
//			sourceFile = new File(fileName);
//			Writer writer = null;
//			try {
//				writer = new FileWriter(sourceFile);
//				writer.write(str);
//				writer.flush();
//			}
//			finally{
//				writer.close();
//			}
//
//		}
//		catch(IOException e)
//		{
//			System.out.println("not good!");
//		}
//		try {
//			BufferedReader file = new BufferedReader(new FileReader(sourceFile));
//			String s = file.readLine();
//			while (s != null){
//				System.out.println(s);
//				s = file.readLine();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		try{
//
//
//			/** Compilation Requirements *********************************************************************************************/
//			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//			StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
//
//			// This sets up the class path that the compiler will use.
//			// I've added the .jar file that contains the DoStuff interface within in it...
//			List<String> optionList = new ArrayList<String>();
//			optionList.add("-classpath");
//			optionList.add(System.getProperty("java.class.path") + ";dist/InlineCompiler.jar");
//
//			Iterable<? extends JavaFileObject> compilationUnit
//			= fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fileName));
//			JavaCompiler.CompilationTask task = compiler.getTask(
//					null,
//					fileManager,
//					diagnostics,
//					optionList,
//					null,
//					compilationUnit);
//			/********************************************************************************************* Compilation Requirements **/
//			if (task.call()) {
//				/** Load and execute *************************************************************************************************/
//				System.out.println("Yipe");
//				// Create a new custom class loader, pointing to the directory that contains the compiled
//				// classes, this should point to the top of the package structure!
//				URLClassLoader classLoader = new URLClassLoader(new URL[]{new File("./").toURI().toURL()});
//				// Load the class from the classloader by name....
//				Class<?> loadedClass = classLoader.loadClass("Code");
//				// Create a new instance...
//				obj = loadedClass.newInstance();
//				// Santity check
//				classLoader.close();
//				fileManager.close();
//				if (obj instanceof DoStuff) {
//					// Cast to the DoStuff interface
//					DoStuff stuffToDo = (DoStuff)obj;
//					// Run it baby
//					return stuffToDo.doStuff();
//				}
//				/************************************************************************************************* Load and execute **/
//			} else {
//				for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
//					System.out.format("Error on line %d in %s%n",
//							diagnostic.getLineNumber(),
//							diagnostic.getSource().toUri());
//				}
//			}
//
//
//		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException exp) {
//			exp.printStackTrace();
//		}
//		return null;
//	}
}



