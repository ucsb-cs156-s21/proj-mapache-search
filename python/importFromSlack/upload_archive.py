import sys
import os
import json
from pymongo import MongoClient

def parse_and_upload():
  if len(sys.argv) != 3:
    print("Usage: python upload_archive.py path/to/archive_folder mongoDBURL")
    exit(1)
  mongoDBURL = sys.argv[2]
  client = MongoClient(mongoDBURL)
  db = client.slack

  archive_path = sys.argv[1]

  users = parse_and_upload_users(archive_path, db)
  channels = parse_and_upload_channels(archive_path, db)

  for channel in channels:
    parse_and_upload_messages(archive_path, channel, db)

  # Create index for messages collection on the channel field
  channel_messages_collection = db[f'messages']
  channel_messages_collection.create_index([("text", "text")])

def parse_and_upload_users(path_to_archive, db):
  path_to_users = os.path.join(path_to_archive, "users.json")
  print(f"Parsing users from {path_to_users}")
  users = []
  with open(path_to_users) as f:
    users = json.load(f)
    users = list(filter(lambda x: not x["is_bot"], users))

  print(f"Uploading {len(users)} users to mongodb")
  users_collection = db["users"]
  users_collection.delete_many({})
  users_collection.insert_many(users)
  return users

def parse_and_upload_channels(archive_path, db):
  path_to_channels = os.path.join(archive_path, "channels.json")
  print(f"Parsing channels from {path_to_channels}")
  channels = []
  with open(path_to_channels) as f:
    channels = json.load(f)

  print(f"Uploading {len(channels)} channels")
  channels_collection = db["channels"]
  channels_collection.delete_many({})
  channels_collection.insert_many(channels)
  return channels

def parse_and_upload_messages(archive_path, channel, db):
  messages_folder = os.path.join(archive_path, channel["name"])
  print(f'Parsing messages for channel {channel["name"]} from {messages_folder}')
  messages = []
  entries = os.listdir(messages_folder)
  for entry in entries:
    path = os.path.join(messages_folder, entry)
    with open(path) as f:
      channel_messages = json.load(f)
      for i in range(len(channel_messages)):
        channel_messages[i]["channel"] = channel["name"]
      messages += channel_messages

  print(f'Uploading {len(messages)} messages from channel {channel["name"]}')
  channel_messages_collection = db[f'messages']
  channel_messages_collection.delete_many({})
  channel_messages_collection.insert_many(messages)
  return messages

if __name__ == "__main__":
  parse_and_upload()
