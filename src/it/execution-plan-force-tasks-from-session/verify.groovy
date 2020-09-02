#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')

assert buildLog.exists()

assert 1 == buildLog.text.count( 'Execution plan for Session tasks: [package]' )  : "Check Session tasks  ->"
assert 1 == buildLog.text.count( 'Force tasks from Session: true' )  : "Check parameter  ->"

assert 1 == buildLog.text.count( '| lifecycle-helper-maven-plugin' )  : "Lifecycle  ->"

assert 2 == buildLog.text.count( '| maven-resources-plugin' )  : "Plugin ->"
assert 2 == buildLog.text.count( '| maven-compiler-plugin' )  : "Plugin ->"
assert 1 == buildLog.text.count( '| maven-surefire-plugin' )  : "Plugin ->"
assert 1 == buildLog.text.count( '| maven-jar-plugin' )  : "Plugin ->"