/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package org.apache.http.client.fluent;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class FluentQuickStart {

    public static void main(String[] args) throws Exception {
    	String codeUrl="http://www1.iicall.com/index.php/main/code";
    	String loginUrl="http://www1.iicall.com/index.php/main/login";
    	HashMap<String, String> postInfo=new HashMap<String, String>();
    	postInfo.put("reguser","13995495987");
    	postInfo.put("password","w1c2l33390");
    	
    	codeUrl = Request.Get(codeUrl).execute().returnContent().asString();
    	codeUrl=codeUrl.substring(codeUrl.indexOf("http"));
    	BufferedImage image = ImageIO.read(new URL(codeUrl));
    	String code = JOptionPane.showInputDialog(new ImageIcon(image),"输入验证码：");
    	if (code!=null) {
    		postInfo.put("code",code);
    		
    		RequestBuilder builder=RequestBuilder.post().setUri(new URI(loginUrl));
//    		Form form=Form.form();
    		for (Iterator<String> iterator = postInfo.keySet().iterator(); iterator.hasNext();) {
				String key = (String)iterator.next();
//				form=form.add(key, postInfo.get(key));
				builder.addParameter(key, postInfo.get(key));
			}
//    		List<NameValuePair> pairs = form.build();
    		HttpUriRequest login=builder.build();
//    		Request.Post(loginUrl).useExpectContinue().bodyForm(pairs).execute();
    		
    		BasicCookieStore cookieStore = new BasicCookieStore();
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();
            
    		CloseableHttpResponse response2 = httpclient.execute(login);
            try {
                HttpEntity entity = response2.getEntity();

                System.out.println("Login form get: " + response2.getStatusLine());
                System.out.println(EntityUtils.toString(entity,"UTF-8"));
                EntityUtils.consume(entity);

                System.out.println("Post logon cookies:");
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    System.out.println("None");
                } else {
                    for (int i = 0; i < cookies.size(); i++) {
                        System.out.println("- " + cookies.get(i).toString());
                    }
                }
            } finally {
                response2.close();
            }
		}else {
			System.out.println("略过...");
		}
    	
    }
}
