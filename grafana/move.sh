#!/bin/sh

mkdir -p /grafanadata/data/plugins/

rm -rf /grafanadata/data/plugins/*

unzip plugins/grafana-simple-json-datasource.zip -d /grafanadata/data/plugins/
unzip plugins/michaeldmoore-michaeldmoore-annunciator-panel.zip -d /grafanadata/data/plugins/
unzip plugins/briangann-grafana-gauge-panel.zip -d /grafanadata/data/plugins/
unzip plugins/savantly-net-grafana-heatmap.zip -d /grafanadata/data/plugins/
unzip plugins/briangann-grafana-datatable-panel.zip -d /grafanadata/data/plugins/
