![Build status](https://travis-ci.com/serendipity-projects/lifecycle-helper-maven-plugin.svg?branch=master)
[![codecov](https://codecov.io/gh/serendipity-projects/lifecycle-helper-maven-plugin/branch/master/graph/badge.svg)](https://codecov.io/gh/serendipity-projects/lifecycle-helper-maven-plugin)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=alert_status)](https://sonarcloud.io/dashboard?id=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin)
[![Maintainability Status](https://sonarcloud.io/api/project_badges/measure?project=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=sqale_rating)](https://sonarcloud.io/component_measures?id=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=sqale_rating)
[![Reliability Status](https://sonarcloud.io/api/project_badges/measure?project=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=reliability_rating)](https://sonarcloud.io/component_measures?id=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=reliability_rating)
[![Coverage ](https://sonarcloud.io/api/project_badges/measure?project=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=coverage)](https://sonarcloud.io/component_measures?id=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=coverage)
[![Vulnerability](https://sonarcloud.io/api/project_badges/measure?project=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=vulnerabilities)](https://sonarcloud.io/component_measures?id=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=vulnerabilities)
[![Lines of code](https://sonarcloud.io/api/project_badges/measure?project=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=ncloc)](https://https://sonarcloud.io/component_measures?id=it.serendigity.maven.plugins%3Alifecycle-helper-maven-plugin&metric=ncloc)


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/it.serendigity.maven.plugins/lifecycle-helper-maven-plugin/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/it.serendigity.maven.plugins/lifecycle-helper-maven-plugin)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Twitter Follow](https://img.shields.io/twitter/follow/SerendigityInfo.svg?style=social)](https://twitter.com/SerendigityInfo)

# Lifecycle Maven Plugin

A Maven 3.x plugin to inspect the lifecycle of your project.
The plugin provides goals aimed at helping to inspects your project lifecycle phases and
if a plugin-goal is executed and when. It includes the ability to list all plugin-goal mapping to executed phases
and group the execution plan result by lifecycle/phases/plugin/goal.

### Quick start
In your maven project execute the following command:

```
mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin
```

Below is an example of a project execution plan with packaging *jar* and no plugins configured (grouped by *lifecycle*)

![Example](images/example_1.jpg)

### Quick links
- Introduction
- Usage
- Goals documentation

### Feature requests
