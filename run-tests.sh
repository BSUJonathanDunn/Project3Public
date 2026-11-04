#!/bin/sh

function header(){
	output=$1
	for i in {1..80} 
	do
		echo -n "-" >> $output
	done
	echo >> $output
}

echo
echo "Compiling the source code"
echo
javac *.java

if ! test -f HashtableExperiment.class
then
	echo
	echo "HashtableExperiment.class not found! Not able to test!! "
	echo
	exit 1
fi

echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "Running test for word-list for varying load factors"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo


dos2unix test-cases/* >& /dev/null
debugLevel=1

for i in 0.5 0.6 0.7 0.8 0.9 0.95 0.99
do
echo
echo "//////////Load Factor = $i//////////"
echo

echo "Running java HashtableExperiment dataSource = 3 loadFactor = $i "
rm -f linear-dump.txt double-dump.txt
java HashtableExperiment 3 $i $debugLevel  >> /dev/null
dos2unix linear-dump.txt double-dump.txt  >& /dev/null
if test ! -f linear-dump.txt || test ! -f double-dump.txt
then
    echo "ERROR: One or both dump files were not generated!"
    continue
fi

echo
diff -w -B  linear-dump.txt test-cases/word-list-$i-linear-dump.txt > diff-linear-$i.out
if test "$?" = 0
then
	echo "Test PASSED for linear probing and load = $i"
	/bin/rm -f diff-linear-$i.out
else
	echo "==> Test FAILED for linear probing load = $i!! "
	echo "       Check the file diff-linear-$i.out for differences"
fi

diff -w -B  double-dump.txt test-cases/word-list-$i-double-dump.txt > diff-double-$i.out
if test "$?" = 0
then
	echo "Test PASSED for double probing and load = $i"
	/bin/rm -f diff-double-$i.out
else
	echo "==> Test FAILED for double probing load = $i!! "
	echo "       Check the file diff-double-$i.out for differences"
fi
done

echo
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
echo "All tests completed!"
echo "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"
