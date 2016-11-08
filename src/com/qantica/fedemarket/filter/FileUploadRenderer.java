package com.qantica.fedemarket.filter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;

/**
 * 
 * @author Maxime Rouillard 
 */
public class FileUploadRenderer extends org.primefaces.component.fileupload.FileUploadRenderer {

    @Override
    public final void decode(FacesContext context, UIComponent component) {
        FileUpload fileUpload = (FileUpload) component;
        String clientId = fileUpload.getClientId(context);
        HttpServletRequestWrapper multipartRequest = getMultiPartRequestInChain(context);

        if (multipartRequest != null) {
            FileItem file = null;
            if (multipartRequest instanceof MultipartRequest) {
                file = ((MultipartRequest) multipartRequest).getFileItem(clientId);
            }
            if (multipartRequest instanceof com.qantica.fedemarket.filter.MultipartRequest) {
                file = ((com.qantica.fedemarket.filter.MultipartRequest) multipartRequest).getFileItem(clientId);
            }

            if (file != null) {
                UploadedFile uploadedFile = new DefaultUploadedFile(file);

                if ("simple".equals(fileUpload.getMode())) {
                    fileUpload.setSubmittedValue(uploadedFile);
                } else {
                    fileUpload.queueEvent(new FileUploadEvent(fileUpload, uploadedFile));
                }
            }
        }
    }

    /**
     * Finds our MultipartRequestServletWrapper in case application contains other RequestWrappers
     */
    private HttpServletRequestWrapper getMultiPartRequestInChain(FacesContext facesContext) {
        Object request = facesContext.getExternalContext().getRequest();

        while (request instanceof ServletRequestWrapper) {
            if (request instanceof MultipartRequest
                    || request instanceof com.qantica.fedemarket.filter.MultipartRequest) {
                return (HttpServletRequestWrapper) request;
            } else {
                request = ((ServletRequestWrapper) request).getRequest();
            }
        }

        return null;
    }
}
