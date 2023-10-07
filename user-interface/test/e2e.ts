import { NightwatchAPI, NightwatchTests, assert, NightwatchBrowser } from 'nightwatch';



const home: NightwatchTests = {
    "Table visible test": () => {
        browser.url("http://localhost:4200").assert.visible("#movies-table");
        browser.end();
    },

    "Title search test": async () => {
        browser.url("http://localhost:4200");

        const searchQuery = "a";

        browser.waitForElementVisible("#search-field", 500)
        browser.setValue("input#search-field", searchQuery);

        browser.waitForElementVisible("#submit-button", 500)
            .click("#submit-button");

        const className = '.cdk-column-name'; // Replace with your actual class name
        while (!await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")) {
            (await browser.findElements(className)).forEach(async (element, index) => {
                if (index > 0)
                    browser.assert.textMatches('tbody>:nth-child(' + (index) + ')>:nth-child(2)', new RegExp(searchQuery, 'i'))
            });
            browser.click(".mat-mdc-paginator-navigation-next");
        }
    }
}

export default home