# Updating Mapache Search from Slack Archive

This directory contains a python script to help upload slack archives to MongoDB.

The script `upload_archive.py` will take a Slack Archive export, and upload it to a MongoDB database.

It expects the database to have three collections:

* `messages`
* `users`
* `channels`

It will remove all previous documents from these three collections before uploading the contents of the Slack Archive
to those collections.

## Quick start

You'll need Python 3 in order to run this script.

You can install dependencies using

```bash
pip install -r requirements.txt
```

You'll need the following values in order to run the script:

- A MongoDB database name (i.e. `coolDB`)
- A MongoDB database username (i.e. `dbUser`)
- A MongoDB database password (i.e. `badPassword`)
- A MongoDB URL (i.e. `"mongodb+srv://<<mongoDBUser>>:<<password>>@cluster0.6bwgb.mongodb.net/<<databaseName>>?retryWrites=true&w=majority"`)
- A path to an unzipped slack archive folder (i.e. `data/path/to/archive`)

Once you have that, you'll be able to run the script as follows.  Note the `""` around the URL; this is typically needed to avoid errors resulting from the embedded characters such as `?` and `&` that have special meanings to the shell.

```bash
python3 upload_archive.py data/path/to/archive "mongodb+srv://dbUser:badPassword@cluster0.GET-THIS-URL-FROM-MONGO.mongodb.net/coolDB?retryWrites=true&w=majority"
```

# CERTIFICATE ERRORS on MAC OS X

See: https://stackoverflow.com/questions/42098126/mac-osx-python-ssl-sslerror-ssl-certificate-verify-failed-certificate-verify
