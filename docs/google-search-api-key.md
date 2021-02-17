# Setting the Google Search API Key

To set the value of  `app.google.search.apiToken` in `temp-credentials.txt`:

1. Visit this link: <https://developers.google.com/custom-search/v1/overview> and then find the button that says: `Get a key`.   Follow the steps from that dialog, and you should be provided with a value something like this one (this is a fake value)

   `AIzaSyB7AwDVhX-ssdf90dfus9d8fasdf`

2. Copy and paste that value in as the value of `app.google.search.apiToken`

3. This value will eventually go into the value of `app.google.search.apiToken` in both `secrets-localhost.properties` and `secrets-heroku.properties`.  