## API Documentation

# App name
cs1699_team_1

# PITT Api

Triggering Events
  - ## 1) User enters Hillman library OR Cathedral
    - **Trigger**: Create Json by using gson library,
    - **Method**: send intent to activity
  ```
  class UserEnterEvent {
      String timestamp;
      String building_name;

      public UserEnterEvent(String t, String b) {
        this.timestamp = t;
        this.building_name = b;
      }
  }
  String info = gson.toJson(new UserEnterEvent(String <unixstdtime>,
      String <buildingname>));
  Intent i = new Intent("team_1.trigger_1")
  i.putExtra("info", info);
  startActivity(i);
  ```

    - User Can: check in and see building facts
  - ## 2) Cathedral or Hillman opens/closes
    - **Trigger**: open / close, send -
    - **Method**: intent to activity
    ```
    class BldgStateEvent {
        String building_name;
        String open_time; // military time
        String close_time;

        public BldgStateEvent(String n, String o, String c) {
          this.building_name = t;
          this.open_time = o;
          this.close_time = c;
        }
    }
    String info = gson.toJson(new BldgStateEvent(String <building>,
        String <open_time>, String <close_time>));
    Intent i = new Intent("team_1.trigger_2")
    i.putExtra("info", info);
    startActivity(i);
    ```
    - User can: see list of open facilities
    - see hours of closed building
  - ## 3) Campus Weather changes  
    - **Trigger**: its raining or snowing on campus,
    - **Method**: Service
      ```
      class WeatherEvent {
          String temperature; // in Fahrenheit
          String weather; // "rain" or "snow"

          public WeatherEvent(String t, String w) {
            this.temperature = t;
            this.weather = w;
          }
      }

      String info = gson.toJson(new WeatherEvent(t,w));
      Intent i = new Intent(team_1.trigger_3);
      i.putExtra("info", info);
      startService(i);
      ```
    - User can: see current temperature
    - [openweathermap](https://openweathermap.org/api)
  - ## 4) PITT Sports
    - **Trigger**: Pitt sports game concludes,
    - **Method**: Broadcast Reciever
      ```
      class SportEvent {
        String sport;
        String opponent_name;
        String result; // "win" or "lose"
        String score;
        String opponent_score;

        public SportEvent (String s, String o, String r, String score, String o_score) {
          this.sport = s;
          this.opponent_name = o;
          this.result = r;
          this.score = score;
          this.opponent_score = o_score;
        }
      }

      String info = gson.toJson(new SportEvent(s, o, r, score, o_score));
      Intent i = new Intent("team_1.trigger_4");
      i.putExtra("info", info);
      sendBroadcast(i);

      ```
    - User can: See sports score.
    - [mysportsfeed](https://www.mysportsfeeds.com/feed-pricing/)
    - [sportsradar](https://sportradar.us/)
