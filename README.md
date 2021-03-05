# FetchRewards

UI:
Grouping by list Id is shown with background color of list item in the recycler view.

Classes:
1.MainActivity
2.CustomAdapter : Recycklerview's adapter.
3.ListItem: This is data type of each list item for the recycler view.

Contents of classes:
1. MainActivity :
   * data to be populated is fetched using async task and HTTPGetCall() method.
   * populateData() method is responsible for setting up a recycler view and it's corresponding adapter with the data previously fetched.
   * cancelled async task in onStop() method to avoid memory leak.

layout files:
1.activity_main  : holds the recycler view .
2.list_item_view : view of each cell of the recycler view. 
