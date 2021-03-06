#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')


assert buildLog.exists()

assert 0 == buildLog.text.count( '| maven-clean-plugin' ) : "Plugin ->"
assert 0 == buildLog.text.count( '| maven-resources-plugin' )  : "Plugin ->"
assert 2 == buildLog.text.count( '| maven-compiler-plugin' )  : "Plugin ->"
assert 0 == buildLog.text.count( '| maven-surefire-plugin' )  : "Plugin ->"
assert 0 == buildLog.text.count( '| maven-jar-plugin' )  : "Plugin ->"
assert 0 == buildLog.text.count( '| maven-install-plugin' )  : "Plugin ->"
assert 0 == buildLog.text.count( '| maven-deploy-plugin' )  : "Plugin ->"
assert 0 == buildLog.text.count( '| maven-site-plugin' )  : "Plugin ->"