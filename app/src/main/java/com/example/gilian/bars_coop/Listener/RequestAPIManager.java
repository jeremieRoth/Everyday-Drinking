package com.example.gilian.bars_coop.Listener;

import java.util.List;

/**
 * Created by gilian on 15/08/2017.
 */

public class RequestAPIManager {
    private RequestAPIListener listener;
    private boolean isLoaded = false;



    public void loaded(List list)
    {
        if(list != null){
            isLoaded = true;
        }

        if(isLoaded)
        {
            this.listener.onRequestSuccessfull();
        }else{
            this.listener.onRequestFailure();
        }

    }
    public RequestAPIListener getListener() {
        return listener;
    }

    public void setListener(RequestAPIListener listener) {
        this.listener = listener;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

}
