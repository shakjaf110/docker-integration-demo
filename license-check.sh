mvn --batch-mode --no-transfer-progress clean license:add-third-party -P license-check -DskipTests
IGNORED_HEADER_LINES=2
COUNTER=0
ERROR_COUNT=0
while read line; do
   if (( $COUNTER < $IGNORED_HEADER_LINES )); then
     COUNTER=$(($COUNTER+1))
     continue
   fi
   if [[ $line == *"(Eclipse Public License - v 1.0)"* ]] ||
      [[ $line == *"(Eclipse Public License 1.0)"* ]] ||
      [[ $line == *"(Eclipse Public License, Version 1.0)"* ]] ||
      [[ $line == *"(Eclipse Distribution License v. 1.0)"* ]] ||
      [[ $line == *"(Eclipse Distribution License - v 1.0)"* ]] ||
      [[ $line == *"(Eclipse Public License v2.0)"* ]] ||
      [[ $line == *"(Eclipse Public License 2.0)"* ]] ||
      [[ $line == *"(Eclipse Public License - v 2.0)"* ]] ||
      [[ $line == *"(Eclipse Distribution License (New BSD License))"* ]] ||
      [[ $line == *"(The Apache Software License, Version 2.0)"* ]] ||
      [[ $line == *"(Apache Software License - Version 2.0)"* ]] ||
      [[ $line == *"(Apache Software License, version 2.0)"* ]] ||
      [[ $line == *"(Apache License, Version 2.0)"* ]] ||
      [[ $line == *"(Apache License, version 2.0)"* ]] ||
      [[ $line == *"(Apache License 2.0)"* ]] ||
      [[ $line == *"(Apache 2.0 License)"* ]] ||
      [[ $line == *"(Apache License v2.0)"* ]] ||
      [[ $line == *"(The Apache License, Version 2.0)"* ]] ||
      [[ $line == *"(Apache 2)"* ]] ||
      [[ $line == *"(Apache 2.0)"* ]] ||
      [[ $line == *"(Apache-2.0)"* ]] ||
      [[ $line == *"(The 2-Clause BSD License)"* ]] ||
      [[ $line == *"(BSD 2-Clause License)"* ]] ||
      [[ $line == *"(BSD-2-Clause)"* ]] ||
      [[ $line == *"(BSD License 3)"* ]] ||
      [[ $line == *"(BSD-3-Clause)"* ]] ||
      [[ $line == *"(Revised BSD)"* ]] ||
      [[ $line == *"(BSD)"* ]] ||
      [[ $line == *"(The BSD License)"* ]] ||
      [[ $line == *"(The BSD 3-Clause License)"* ]] ||
      [[ $line == *"(BSD License)"* ]] ||
      [[ $line == *"(Bouncy Castle Licence)"* ]] ||
      [[ $line == *"(EDL 1.0)"* ]] ||
      [[ $line == *"(EPL 2.0)"* ]] ||
      [[ $line == *"(CDDL/GPLv2+CE)"* ]] ||
      [[ $line == *"(CDDL+GPL License)"* ]] ||
      [[ $line == *"(CDDL + GPLv2 with classpath exception)"* ]] ||
      [[ $line == *"(GNU Lesser General Public License)"* ]] ||
      [[ $line == *"(GNU Lesser General Public Licence)"* ]] ||
      [[ $line == *"(The MIT License (MIT))"* ]] ||
      [[ $line == *"(The MIT License)"* ]] ||
      [[ $line == *"(MIT License)"* ]] ||
      [[ $line == *"(MIT)"* ]] ||
      [[ $line == *"(MIT-0)"* ]] ||
      [[ $line == *"(MPL 2.0 or EPL 1.0)"* ]] ||
      [[ $line == *"(EPL 1.0) (MPL 2.0)"* ]] ||
      [[ $line == *"(Mozilla Public License)"* ]] ||
      [[ $line == *"(Mozilla Public License, Version 2.0)"* ]] ||
      [[ $line == *"(Unknown license)"* ]] ||
      [[ $line == *"(Common Development and Distribution License)"* ]] ||
      [[ $line == *"(CDDL 1.1)"* ]] ||
      [[ $line == *"(GNU Library General Public License v2.1 or later)"* ]] ||
      [[ $line == *"(CC0)"* ]] ||
      [[ $line == *"(CC0 1.0 Universal)"* ]] ||
      [[ $line == *"(Public Domain)"* ]] ||
      [[ $line == *"(Public Domain, per Creative Commons CC0)"* ]]; then
    continue
   fi;
   ERROR_COUNT=$(($ERROR_COUNT+1))
   echo "$line"
done <target/generated-sources/license/THIRD-PARTY.txt
if (( $ERROR_COUNT > 0 )); then
  echo "$ERROR_COUNT errors found, please consult the project management to see if the flagged libraries/licenses above can be whitelisted, too"
  exit 1
else
  echo "Everything fine, no Error"
  exit 0
fi
