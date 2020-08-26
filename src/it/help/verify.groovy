#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')


assert buildLog.exists()

assert 1 == buildLog.text.count( 'This plugin has 3 goals:' )
