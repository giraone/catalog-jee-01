iso-3166-1-alpha2
http://data.okfn.org/data/core/country-list
http://www.addressdoctor.com/de/de/laender-daten/iso-laendercodes.html#fbid=rOyYMecfu9r

$ awk -F, '{ printf "\"%s\",\"%s\"\r\n", $11, $2 }' 
$ awk -F, '{ printf "\"%s\",\"%s\"\r\n", $2, $1 }'
