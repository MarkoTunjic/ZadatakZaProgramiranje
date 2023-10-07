exports.assertTextInArray = function (elements, searchText, message) {
    this.api.perform(function () {
        let isTextFound = false;

        elements.forEach(function (element, index) {
            const elementText = element.getText();
            if (elementText.includes(searchText)) {
                isTextFound = true;
                return; // Exit the loop if a match is found
            }
        });

        this.assert.ok(isTextFound, message);
    });

    return this; // Return the browser object for chaining
};