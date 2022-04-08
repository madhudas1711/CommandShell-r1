<a href="https://cdap-users.herokuapp.com/"><img alt="Join CDAP community" src="https://cdap-users.herokuapp.com/badge.svg?t=to-utf8-action"/></a> [![Build Status](https://travis-ci.org/hydrator/to-utf8-action.svg?branch=release/1.0)](https://travis-ci.org/hydrator/to-utf8-action) [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) <img alt="CDAP Action" src="https://cdap-users.herokuapp.com/assets/cdap-action.svg"/> []()
# Example Action Plugin

Description
-----------
This action plugin can be used as a starting point for developing your own action plugin.

Use Case
--------
This plugin is used when the user wants to build their own Action plugin. They can clone this repo and
make the changes they need.

Properties
----------
| Configuration | Required | Default | Description |
| :------------ | :------: | :------ | :---------- |
| **Example Config Option** | **N** | None | This is just an example configuration option. |

Getting Started
===============

Prerequisites
--------------
CDAP version 4.2.x or higher.

Building Plugins
----------------
You get started with File Contents action plugin by building directly from the latest source code::

   git clone git@github.com:hydrator/&lt;artifact-id&gt;.git
   cd &lt;plugin-directory&gt;
   mvn clean package

After the build completes, you will have a JAR for each plugin under each
``<plugin-name>/target/`` directory.

Deploying Plugins
-----------------
You can deploy a plugin using the CDAP CLI::

  > load artifact &lt;target/plugin-jar&gt; config-file <resources/plugin-config>

  > load artifact target/&lt;artifact-id&gt;-&lt;version&gt;.jar \
         config-file target/&lt;artifact-id&gt;-&lt;version&gt;.json

You can build without running tests: ``mvn clean install -DskipTests``

Mailing Lists
-------------
CDAP User Group and Development Discussions:

- [cdap-user@googlegroups.com](https://groups.google.com/d/forum/cdap-user)

The *cdap-user* mailing list is primarily for users using the product to develop
applications or building plugins for appplications. You can expect questions from
users, release announcements, and any other discussions that we think will be helpful
to the users.

Slack Channel
-------------
[CDAP Slack Channel](http://cdap-users.herokuapp.com)

License and Trademarks
======================

Copyright © 2017 Cask Data, Inc.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the
License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
either express or implied. See the License for the specific language governing permissions
and limitations under the License.

Cask is a trademark of Cask Data, Inc. All rights reserved.

Apache, Apache HBase, and HBase are trademarks of The Apache Software Foundation. Used with
permission. No endorsement by The Apache Software Foundation is implied by the use of these marks.
