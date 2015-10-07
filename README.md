# Popular-Movies-app
Android app to explore movies from the tmdb api. It's the final project of Udacity course : [Developing Android Apps: Android Fundamentals](https://www.udacity.com/course/ud853)

My App is evaluated as `Exceeds Specifications` by the `Udacity Team`.
![alt text](../master/device_art/Evaluation.JPG "Udacity Review")



### Features:
1. explore popular movies provided by the api
2. explore most rated movies provided by the api
3. Show movies trailers and reviews.
4. Add movies to favorites
5. Support phones and tablets
6. Explore content offline by saving last downloaded data
7. Material design with the new Floating Action Button and Recycler View
8. On online user can share movie trailer by share action provider


## Technical Details:
- The app gets data from the api and store it into a database 
- Content Provider updates the UI from the database
- on offline the user could explore most recent downloaded data for popular and most rated movies and all favorites movies
and images are loaded from cache
- on online when new data coming it replaces the old data 
- As a result of this algorithm **the user won't see a blank page at any time** and content will load very fast.

## Screensots:
#### Phone:
 ![alt text](../master/device_art/Phone (1).png)
 ![alt text](../master/device_art/Phone (2).png)
 ![alt text](../master/device_art/Phone (3).png)
 ![alt text](../master/device_art/Phone (4).png)

#### Tablet:
 ![alt text](../master/device_art/Tablet (1).png)
 ![alt text](../master/device_art/Tablet (2).png)
 ![alt text](../master/device_art/Tablet (3).png)
 ![alt text](../master/device_art/Tablet (4).png)
 ![alt text](../master/device_art/Tablet (5).png)


## Notes:
To build the app you should provide your key for TMDB API in ConnectionUtilities class.



### Libraries Used:
- [Picasso](http://square.github.io/picasso/)
- [EndlessRecyclerViewAdapter](https://github.com/rockerhieu/rv-adapter-endless)
