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

        const className = '.cdk-column-name';
        let disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")
        do {
            (await browser.findElements(className)).forEach(async (element, index) => {
                if (index > 0)
                    browser.assert.textMatches('tbody>:nth-child(' + (index) + ')>:nth-child(2)', new RegExp(searchQuery, 'i'))
            });
            if (!disabled)
                browser.click(".mat-mdc-paginator-navigation-next");
        } while (!(disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")));
    },

    "Genre search test": async () => {
        browser.url("http://localhost:4200");

        const genres = ["Comedy", "Horror"]
        browser.waitForElementVisible("#dropdown").click("#dropdown");

        browser.waitForElementVisible("#dropdown-panel", 500)
        for (let genre of genres) {
            browser.click("mat-option[ng-reflect-value=" + genre + "]")
        }

        browser.click(".cdk-overlay-backdrop");

        browser.waitForElementVisible("#submit-button", 500)
            .click("#submit-button");

        const className = '.cdk-column-genreName';
        let disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")
        do {
            (await browser.findElements(className)).forEach(async (element, index) => {
                let currentGenre: string = await browser.elementIdText(element.getId());
                currentGenre = await browser.elementIdText(element.getId())//for some reason it gotta be done twice
                if (index > 0) {
                    let found = false;
                    for (let genre of genres) {
                        if (currentGenre === genre) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        assert.fail("Genre not in filtered genres");
                    } else {
                        assert.ok("true");
                    }
                }


            });
            if (!disabled)
                browser.click(".mat-mdc-paginator-navigation-next");
        } while (!(disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")));
    }
}

export default home