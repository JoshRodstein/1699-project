## API Documentation

Questions for Sharif:

  1. IF our triggers our our external in nature.. Such as weather (as in your example), then how do other apps trigger these events?
    - How would the previous app in the app chain trigger event D, from example 1.
  2. Should we define the intents etc. we wish to receive, or should we define the intents we wish to send?




PITT Api

Services
  - Building / Facility Tracker
    - add building/facility to watch list
    - Location
      - GPS, Google Maps API
    - Capacity
      - User sourced locations
    - Facility Info
      - Equipment available
      - Quiet, Library, Etc..

  - Note Sharing / Messaging
    - Target Email, Messaging


Triggering Events
  1. User enters study hall location radius
    - Triggers: Message with information
  2. Facility on watch list changes state
    - open / close
    - Sub Facilities: open/close
  3. Campus Weather changes  
    - User defined intervals of weather checking (Timer)
    - [openweathermap](https://openweathermap.org/api)
  4. PITT Sports
    - Score Reporting / Alerts
      - Final / Periodic score updates
    - [mysportsfeed](https://www.mysportsfeeds.com/feed-pricing/)
    - [sportsradar](https://sportradar.us/)
