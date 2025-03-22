import {useState} from "react";

function Search() {
    const [searchText, setSearchText] = useState('');

    function onSearchButton () {
        console.log("searchText: ", searchText);
    }

    function onChangeText (value) {
        console.log("onChangeText: ", value);
        setSearchText(value)
    }

    return (
        <>
            <div className="form-group d-flex flex-column justify-content-center align-items-center p-4 gap-4 text-white">
                <div className="h2">Discovery life of a historical character!</div>
                {/*Modificare width con la lunghezza del div sopra*/}
                <input
                    style={{ width: 552 }}
                    className="form-control"
                    type="text"
                    placeholder="Search historical character..."
                    value={searchText}
                    onChange={e => onChangeText(e.target.value)}
                />
                <button className="btn btn-dark" onClick={onSearchButton}>Search</button>
            </div>
        </>
    )
}

export default Search;