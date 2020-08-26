#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')


assert buildLog.exists()

assert 1 == buildLog.text.count( 'Lifecycle: clean' )  : "Lifecycle  ->"
assert 1 == buildLog.text.count( 'Lifecycle: default' )  : "Lifecycle  ->"
assert 1 == buildLog.text.count( 'Lifecycle: site' )  : "Lifecycle  ->"

assert 1 == buildLog.text.count( '| maven-clean-plugin' )  : "Plugin ->"
assert 2 == buildLog.text.count( '| maven-resources-plugin' )  : "Plugin ->"
assert 2 == buildLog.text.count( '| maven-compiler-plugin' )  : "Plugin ->"
assert 1 == buildLog.text.count( '| maven-surefire-plugin' )  : "Plugin ->"
assert 1 == buildLog.text.count( '| maven-jar-plugin' )  : "Plugin ->"
assert 1 == buildLog.text.count( '| maven-install-plugin' )  : "Plugin ->"
assert 1 == buildLog.text.count( '| maven-deploy-plugin' )  : "Plugin ->"
assert 2 == buildLog.text.count( '| maven-site-plugin' )  : "Plugin ->"