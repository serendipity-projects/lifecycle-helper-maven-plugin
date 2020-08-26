#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')


assert buildLog.exists()

assert 0 == buildLog.text.count( '| maven-clean-plugin' )
assert 0 == buildLog.text.count( '| maven-resources-plugin' )
assert 2 == buildLog.text.count( '| maven-compiler-plugin' )
assert 0 == buildLog.text.count( '| maven-surefire-plugin' )
assert 0 == buildLog.text.count( '| maven-jar-plugin' )
assert 0 == buildLog.text.count( '| maven-install-plugin' )
assert 0 == buildLog.text.count( '| maven-deploy-plugin' )
assert 2 == buildLog.text.count( '| maven-site-plugin' )