package com.example.andtestfacebook;

import java.util.Arrays;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;


public class MainActivity extends Activity {

	Button btn1;
	
	CallbackManager callbackManager;
	private AccessToken accessToken;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {


    	FacebookSdk.sdkInitialize(getApplicationContext());
    	
        super.onCreate(savedInstanceState);
       
        
        //init FB
       
        
        setContentView(R.layout.activity_main);
      
        
        //�ŧicallback Manager

        callbackManager = CallbackManager.Factory.create();

        //���button

        Button loginButton = (Button) findViewById(R.id.button1);

        loginButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
            	

                Log.d("FB", "access token got.");
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this, Arrays.asList("public_profile", "email"));
            }
            
        });
        

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            //�n�J���\

            @Override
            public void onSuccess(LoginResult loginResult) {

                //accessToken����γ\�ٷ|�Ψ� ���s�_��

                accessToken = loginResult.getAccessToken();

                Log.d("FB", "access token got.");

                //send request and call graph api

                GraphRequest request = GraphRequest.newMeRequest(
                        accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {

                            //��RESPONSE�^�Ӫ��ɭ�

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {

                                //Ū�X�m�W ID FB�ӤH�����s��

                            	
                            	
                                Log.d("FB", "complete");
                                Log.d("FB", object.optString("name"));
                                Log.d("FB", object.optString("link"));
                                Log.d("FB", object.optString("id"));

                            }
                        });

                //�]�J�A�Q�n�o�쪺��� �e�Xrequest

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link");
                request.setParameters(parameters);
                request.executeAsync();
            }

            //�n�J����

            @Override
            public void onCancel() {
                // App code

                Log.d("FB", "CANCEL");
            }

            //�n�J����

            @Override
            public void onError(FacebookException exception) {
                // App code

                Log.d("FB", exception.toString());
            }
        });
        
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
    
    
    
}
