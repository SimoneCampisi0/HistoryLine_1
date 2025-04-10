import {useEffect, useState} from "react";
import {getSuggestCharacters} from "../../services/CharacterService.js";
import './Search.css';

function Search() {
    const [searchText, setSearchText] = useState(undefined);
    const [errorMessage, setErrorMessage] = useState(null);
    const [suggestResults, setSuggestResults] = useState([]);
    const [showSuggest, setShowSuggest] = useState(false);

    async function getSuggestCharactersList() {
        const req = {
            name: searchText,
        }
        return await getSuggestCharacters(req);
    }

    function clearSearch() {
        setSearchText("");
        setSuggestResults(null);
        console.log("clearSearch");
    }

    function validateText() {
        if (searchText.trim() === "") {
            setErrorMessage({
                errorName: "Search empty.",
                message: "Insert a text to search.",
                code: "001",
            })
            return false;
        }
        return true;
    }

    function onSelectedValue (item) {
        console.log("searched: ", item);
        setShowSuggest(false);
        setErrorMessage(null);
    }

    function onSearchButton () {
        console.log("searched: ", searchText);
        setErrorMessage(null);
    }

    useEffect(() => {
        if(searchText === undefined) {
            console.log("searchText is undefined");
            return;
        }

        setShowSuggest(true);

        const debounce = setTimeout(() => {
            if(!validateText()) {
                clearSearch();
                return;
            }
            getSuggestCharactersList(searchText)
                .then(r => {
                    console.log("response: ", r);
                    setSuggestResults(r.items);
                });
        }, 300);

        return () => clearTimeout(debounce); // Pulisco il timer se searchText varia prima che il timer scada
    }, [searchText]); // In questo modo viene rilevato ogni cambiamento allo state di searchText


    return (
        <>
            <div className="form-group d-flex flex-column justify-content-center align-items-center p-4 text-white  mt-5">
                <div className="h2 mb-4">Discovery life of a historical character!</div>
                {/*Modificare width con la lunghezza del div sopra*/}
                <input
                    style={{ width: 552 }}
                    className="form-control "
                    type="text"
                    placeholder="Search historical character..."
                    value={searchText}
                    onChange={e => setSearchText(e.target.value)}
                />
                {suggestResults && showSuggest && suggestResults.length > 0 &&
                    (
                        <div className="mt-1 mb-3">
                            <>
                                {suggestResults.map((item, index) => (
                                    // <div className="rounded suggest-result" key={index} onClick={() => onSelectedValue(item)}>{item.itemLabel}</div>
                                    <div className="suggest-result" key={index} onClick={() => onSelectedValue(item)}>{item.itemLabel}</div>
                                ))}
                            </>
                        </div>
                    )
                }
                {errorMessage && (
                    <div className="mt-4 mb-2">
                        {errorMessage.message}
                    </div>
                )}
                <button className="btn btn-dark mt-4" onClick={onSearchButton}>Search</button>
            </div>
        </>
    )
}

export default Search;