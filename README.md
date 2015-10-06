# Popular-Movies-app
Android app to explore movies from the tmdb api. It's the final project of Udacity course : [Developing Android Apps: Android Fundamentals](https://www.udacity.com/course/ud853)


### Features:
1. explore popular movies provided by the api
2. explore most rated movies provided by the api
3. Show movies trailers and reviews.
4. Add movies to favorites
5. Support phones and tablets
6. Explore content offline by saving last downloaded data
7. Adapt material design and the new Floating Action Button
8. On online user can share movie trailer by share action provider


## Technical Details:
- The app gets data from the api and store it into a database 
- Content Provider updates the UI from the database
- on offline the user could explore most recent downloaded data for popular and most rated movies and all favorites movies
and images are loaded from cache
- on online when new data coming it replaces the old data 
as a result the user won't see a blank page at any time.

## Screensots:
#### Phone:
 ![alt text](../master/device_art/Phone (1).png "Phone Main Activity - Portrait")


#### Tablet:




### Libraries Used:
- [Picasso](http://square.github.io/picasso/)
- [EndlessRecyclerViewAdapter](https://github.com/rockerhieu/rv-adapter-endless)
