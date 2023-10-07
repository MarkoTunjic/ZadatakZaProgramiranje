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
    },

    "Start date search test": async () => {
        browser.url("http://localhost:4200");

        const startDate = "10/10/2020";

        browser.waitForElementVisible("#start-date", 500)
        browser.setValue("input#start-date", startDate);

        browser.waitForElementVisible("#submit-button", 500)
            .click("#submit-button");

        const className = '.cdk-column-addingDate';
        let disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")
        let cond = new Date(startDate);
        do {
            (await browser.findElements(className)).forEach(async (element, index) => {
                if (index > 0) {
                    let date = new Date(await browser.elementIdText(element.getId()));
                    if (date < cond) {
                        assert.fail("date before start date");
                    } else {
                        assert.ok("true");
                    }
                }
            });
            if (!disabled)
                browser.click(".mat-mdc-paginator-navigation-next");
        } while (!(disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")));
    },
    "End date search test": async () => {
        browser.url("http://localhost:4200");

        const endDate = "10/10/2022";

        browser.waitForElementVisible("#end-date", 500)
        browser.setValue("input#end-date", endDate);

        browser.waitForElementVisible("#submit-button", 500)
            .click("#submit-button");

        const className = '.cdk-column-addingDate';
        let disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")
        let cond = new Date(endDate);
        do {
            (await browser.findElements(className)).forEach(async (element, index) => {
                if (index > 0) {
                    let date = new Date(await browser.elementIdText(element.getId()));
                    if (date > cond) {
                        assert.fail("date after end date");
                    } else {
                        assert.ok("true");
                    }
                }
            });
            if (!disabled)
                browser.click(".mat-mdc-paginator-navigation-next");
        } while (!(disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")));
    },

    "All filter test": async () => {
        browser.url("http://localhost:4200");

        //title search
        const searchQuery = "a";
        browser.waitForElementVisible("#search-field", 500)
        browser.setValue("input#search-field", searchQuery);

        //genre search
        const genres = ["Comedy", "Adventure"]
        browser.waitForElementVisible("#dropdown").click("#dropdown");
        browser.waitForElementVisible("#dropdown-panel", 500)
        for (let genre of genres) {
            browser.click("mat-option[ng-reflect-value=" + genre + "]")
        }
        browser.click(".cdk-overlay-backdrop");

        //start date
        const startDate = "10/10/2021";
        browser.waitForElementVisible("#start-date", 500)
        browser.setValue("input#start-date", startDate);

        //end date
        const endDate = "10/10/2024";
        browser.waitForElementVisible("#end-date", 500)
        browser.setValue("input#end-date", endDate);

        browser.waitForElementVisible("#submit-button", 500)
            .click("#submit-button");

        let disabled = await browser.getAttribute(".mat-mdc-paginator-navigation-next", "disabled")
        let end = new Date(endDate);
        let start = new Date(startDate);
        do {
            (await browser.findElements(".cdk-column-name")).forEach(async (element, index) => {
                if (index > 0)
                    browser.assert.textMatches('tbody>:nth-child(' + (index) + ')>:nth-child(2)', new RegExp(searchQuery, 'i'))
            });

            (await browser.findElements(".cdk-column-genreName")).forEach(async (element, index) => {
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

        //for some reason if everything under 1 loop it breaks
        do {
            (await browser.findElements('.cdk-column-addingDate')).forEach(async (element, index) => {
                if (index > 0) {
                    let date = new Date(await browser.elementIdText(element.getId()));
                    if (date < start) {
                        assert.fail("date before start date");
                    } else {
                        assert.ok("true");
                    }
                }
            });
            (await browser.findElements('.cdk-column-addingDate')).forEach(async (element, index) => {
                if (index > 0) {
                    let date = new Date(await browser.elementIdText(element.getId()));
                    if (date > end) {
                        assert.fail("date after end date");
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