GridImageView
=============

Android app for searching and filtering through images via the Google Image Search.

### Completed User Stories

__Required__
* User can enter a search query that will display a grid of image results from the Google Image API.
* User can tap on any image in results to see the image full-screen
* User can click on "settings" which allows selection of advanced search options to filter results
* User can configure advanced search filters such as:
  * Size (small, medium, large, extra-large)
  * Color filter (black, blue, brown, gray, green, etc...)
  * Type (faces, photo, clip art, line art)
  * Site (espn.com)
* Subsequent searches will have any filters applied to the search results
* User can scroll down “infinitely” to continue loading more image results (up to 8 pages)

- - -

_Optional_
* Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* Advanced: User can share an image to their friends or email it to themselves
* Advanced: Replace Filter Settings Activity with a lightweight modal overlay
* Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
* Advanced: Robust error handling, check if internet is available, handle error cases, network failures
* Bonus: Use the StaggeredGridView to display improve the grid of image results
* Bonus: User can zoom or pan images displayed in full-screen detail view
