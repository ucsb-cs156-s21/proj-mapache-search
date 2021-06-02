# Setting the MongoDB URI

To set the value of  `spring.data.mongodb.uri` in `temp-credentials.txt`:

1. For this app, it is likely that the staff will
   be setting up a MongoDB Database for you, 
   and will provide you with a string like the following
   one 
   
   ```
   mongodb+srv://<username>:<password>@cluster0.a1bcd.mongodb.net/<dbname>?retryWrites=true&w=majority
   ```

   (If you were not supplied by the course staff with a database string, ask the staff before setting up your own.  If you are working with this project outside of the course, you may set up your own cluster at <https://cloud.mongodb.com/>)
)

   It is important to note that `<username>`, `<password>` and `<dbname>` need to be replaced with actual values that you would be supplied with separately.  For example, if you are supplied with:

   ```
   username=team5ReadOnly
   password=z78sviz7kEzilhs8vhzsd
   dbname=database
   ```

   You would need to plug those values into the string above, resulting in the string below.  Note that when you replace the values, you do not include the `<>` characters:

   ```
   mongodb+srv://team5ReadOnly:z78sviz7kEzilhs8vhzsd@cluster0.a1bcd.mongodb.net/database?retryWrites=true&w=majority
   ```


2. Copy and paste that value (the new string with the  `<username>`, `<password>` and `<dbname>` plugged in) as the value of `spring.data.mongodb.uri`

3. This value will eventually go into the value of `spring.data.mongodb.uri` in both `secrets-localhost.properties` and `secrets-heroku.properties`. 
