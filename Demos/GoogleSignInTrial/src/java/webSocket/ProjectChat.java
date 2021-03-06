/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSocket;

import webSocket.*;
import com.google.gson.Gson;
import ejemplo.collection.MessageChat;
import ejemplo.collection.Projects;
import ejemplo.service.ProjectsService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




/**
 *
 * @author aitorpagan
 */

@Component
public class ProjectChat implements Serializable{
    
    @Autowired
    ProjectsService projectsService;
    
    private Gson gson = new Gson();    
    protected String projectId;
    protected List<MessageChat> mychat = new ArrayList<>();
    
    public ProjectChat(){
    }
    
    public ProjectChat(String projectId){
        
        
        this.projectId = projectId;
        Projects ps = projectsService.findProject(projectId);
        mychat = ps.getChat();

    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<MessageChat> getMychat() {
        return mychat;
    }

    public void setMychat(List<MessageChat> mychat) {
        this.mychat = mychat;
    }
    
    public void addMessageToChat(String message){
        MessageChat mc = gson.fromJson(message, MessageChat.class);
        mychat.add(mc);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.projectId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProjectChat other = (ProjectChat) obj;
        if (!Objects.equals(this.projectId, other.projectId)) {
            return false;
        }
        return true;
    }
    
    public void saveChat(){
        Projects ps = projectsService.findProject(projectId);
        ps.setChat(mychat);
//        String toByteArray = gson.toJson(mychat);
//        byte[] newchat = toByteArray.getBytes();
//        ps.setChat(newchat);
//        proyectoScrumFacade.edit(ps);
        projectsService.editProjects(ps);
    }

    
}
