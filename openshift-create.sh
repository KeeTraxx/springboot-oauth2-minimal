#!/usr/bin/env bash

# doesn't work somehow...
#oc new-app \
#    jorgemoralespou/s2i-java~https://github.com/KeeTraxx/springboot-oauth2-minimal.git \
#    -p SECURITY_OAUTH2_CLIENT_CLIENTID=XXX \
#    -p SECURITY_OAUTH2_CLIENT_CLIENTSECRET=XXX \
#    -p SECURITY_OAUTH2_CLIENT_ACCESSTOKENURI=XXX \
#    -p SECURITY_OAUTH2_CLIENT_USERAUTHORIZATIONURI=XXX \
#    -p SECURITY_OAUTH2_RESOURCE_USER_INFO_URI=XXX


oc new-build --name=web redhat-openjdk18-openshift --binary=true

oc start-build web --from-file=build/libs/keycloak-minimal-1.0-SNAPSHOT.jar --follow

oc new-app web \
    -p SECURITY_OAUTH2_CLIENT_CLIENTID=XXX \
    -p SECURITY_OAUTH2_CLIENT_CLIENTSECRET=XXX \
    -p SECURITY_OAUTH2_CLIENT_ACCESSTOKENURI=XXX \
    -p SECURITY_OAUTH2_CLIENT_USERAUTHORIZATIONURI=XXX \
    -p SECURITY_OAUTH2_RESOURCE_USER_INFO_URI=XXX
