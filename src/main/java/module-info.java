module ServerAppChat {
  requires com.google.gson;
  requires java.sql;
  opens com.igorbulinski.chat.server to com.google.gson;
  exports com.igorbulinski.chat.server;
}