package com.armyPOC.emailService.service;

import com.armyPOC.emailService.config.JmsConfig;
import com.armyPOC.emailService.config.RestClient;
import com.armyPOC.emailService.model.Email;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import io.reactivex.Single;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebsocketListenerService
{

   private HubConnection hubConnection;
   private JmsTemplate jmsTemplate;


   private RestClient restClient;
   //      private static final String URI = "/workflows/2d708852604e434e9513b10ee13849ea/triggers/manual/paths/invoke/1/email?api-version=2016-10-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=6D0BbKXR-LSVsjNRoGy6TwhMQmjzoSovGuaCqY_ycEU";
   private static final String hubURL = "https://army-poc.service.signalr.net/client/?hub=ArmyHub";
   private static final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiJNaWtlQSIsIm5iZiI6MTU2Mzk4MzM4MCwiZXhwIjoxNTY0MDE5MzgwLCJpYXQiOjE1NjM5ODMzODAsImF1ZCI6Imh0dHBzOi8vYXJteS1wb2Muc2VydmljZS5zaWduYWxyLm5ldC9jbGllbnQvP2h1Yj1Bcm15SHViIn0.WXplml8XxixiIGs1jclh1SC-nAfgcJ1P7cCpHi5vOIM";

   public WebsocketListenerService() {

      hubConnection = HubConnectionBuilder.create(hubURL)
            .withAccessTokenProvider(Single.defer(() -> {
               // Your logic here.
               return Single.just(token);
            })).build();

      restClient = new RestClient();

   }

   public WebsocketListenerService(JmsTemplate jmsTemplate) {
      this.jmsTemplate = jmsTemplate;
   }

   public void startClient() {

      hubConnection.on("SendEmail", (message, email) -> {
         jmsTemplate.convertAndSend(JmsConfig.JMS_TOPIC_MAIL, new Email(
               email.getTo(), email.getBody()));
      }, String.class, Email.class);


      //This is a blocking call
      hubConnection.start().blockingAwait();

   }

   //   private void readResponse(Email email) {
//
//      // TODO - this will need to read a response from a different endpoint that triggers an email to be sent
//
//      java.net.URI url = UriComponentsBuilder.newInstance().scheme("https")
//            .host("prod-80.westus.logic.azure.com")
//            .pathSegment("workflows")
//            .pathSegment("2d708852604e434e9513b10ee13849ea")
//            .pathSegment("triggers")
//            .pathSegment("manual")
//            .pathSegment("paths")
//            .pathSegment("invoke")
//            .pathSegment("1")
//            .pathSegment("email")
//            .queryParam("api-version", "2016-10-01")
//            .queryParam("sp", "%2Ftriggers%2Fmanual%2Frun")
//            .queryParam("sv", "1.0")
//            .queryParam("sig", "6D0BbKXR-LSVsjNRoGy6TwhMQmjzoSovGuaCqY_ycEU")
//            .build(true).toUri();
//
//      restClient.post(url, email);
//   }
}
