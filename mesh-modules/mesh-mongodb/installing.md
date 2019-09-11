# Install MongoDB Community Edition on macOS

## Overview

The following tutorial uses [Homebrew](https://brew.sh/?jmp=mongodb-docs) to install MongoDB 4.2 Community Edition on macOS systems. To install a different version of MongoDB, please refer to that version’s documentation.


## Platform Support
MongoDB 4.2+ only supports macOS versions 10.12+ on Intel x86-64. See Supported Platforms for more information.

##Tap the MongoDB Homebrew Tap

    brew tap mongodb/brew
 
## Install MongoDB

    brew install mongodb-community@4.2

1. the configuration file (/usr/local/etc/mongod.conf)
3. the log directory path (/usr/local/var/log/mongodb)
3. the data directory path (/usr/local/var/mongodb)

Run MongoDB

    mongod --config /usr/local/etc/mongod.conf
    
Connect and Use MongoDB

To begin using MongoDB, connect a mongo shell to the running instance. From a new terminal, issue the following:

    mongo
