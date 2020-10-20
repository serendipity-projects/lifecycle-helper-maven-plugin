#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')

assert buildLog.exists()

assert 1 == buildLog.text.count( 'Skipping the execution as per configuration' )  : "Check log execution  ->"
assert 0 == buildLog.text.count( 'Output directory :' )  : "Check log execution, no log  ->"