package com.qantica.fedemarket.managebean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;


@ManagedBean
public class FileBean {
	
	private UploadedFile file;  
	  
    public UploadedFile getFile() {  
        return file;  
    }  
  
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }  
  
    public void upload() {  
        if(file != null) {  
            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
            
            try {
	        	
	            copyFile(file.getFileName(), file.getInputstream());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
        }  
    } 

	
	public void copyFile(String fileName, InputStream in) {

		 try {
			 
			 File mFile = new File("../contenidos/" + fileName);
             // write the inputStream to a FileOutputStream
             OutputStream out = new FileOutputStream(mFile);
          
             int read = 0;
             byte[] bytes = new byte[1024];
          
             while ((read = in.read(bytes)) != -1) {
                 out.write(bytes, 0, read);
             }
          
             in.close();
             out.flush();
             out.close();
          
             System.out.println("[Upload] - Archivo Creado!");
             
             } catch (IOException e) {
            	 System.out.println("[Upload] - Error Cargando el Archivo!");
             }
		
	} 
	
}