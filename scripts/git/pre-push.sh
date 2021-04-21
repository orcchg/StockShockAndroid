echo "Running static analysis..."

./gradlew   ktlintCheck --continue

status=$?

if [ "$status" = 0 ] ; then
    echo
    echo "Static analysis found no violations"
    echo
else
    echo
    echo 1>&2 "Found some violations"
    echo
    exit 1
fi
