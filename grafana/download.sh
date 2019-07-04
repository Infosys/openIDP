#!/bin/sh

smart_download(){
	md5line=$(cat checksum | grep $1)
	md5gen=$(md5sum $1)
	if [ "$md5gen" = "$md5line" ]; then
		echo "File already exists at $1"
	else
		echo "Downloading File at $1"
		wget $WGET_PROXY -O  $1 $2 --no-check-certificate
	fi
}

mkdir -p plugins

echo "Downloading Grafana Plugins"
smart_download plugins/grafana-simple-json-datasource.zip https://grafana.com/api/plugins/grafana-simple-json-datasource/versions/1.4.0/download
smart_download plugins/michaeldmoore-michaeldmoore-annunciator-panel.zip https://grafana.com/api/plugins/michaeldmoore-annunciator-panel/versions/1.0.0/download
smart_download plugins/briangann-grafana-gauge-panel.zip https://grafana.com/api/plugins/briangann-gauge-panel/versions/0.0.6/download
smart_download plugins/savantly-net-grafana-heatmap.zip https://grafana.com/api/plugins/savantly-heatmap-panel/versions/0.2.0/download
smart_download plugins/briangann-grafana-datatable-panel.zip https://grafana.com/api/plugins/briangann-datatable-panel/versions/0.0.6/download

