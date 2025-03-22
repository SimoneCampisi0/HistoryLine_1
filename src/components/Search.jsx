function Search() {
    function onSearchButton () {
        console.log("onSearchButton")
    }

    return (
        <>
            <div className="d-flex flex-column justify-content-center align-items-center p-4 gap-4 text-white">
                <div className="h2">Discovery life of a historical character!</div>
                {/*Modificare width con la lunghezza del div sopra*/}
                <input style={{ padding: "0.50rem", width: 552 }} className="border rounded" type="text" />
                <button onClick={onSearchButton}>Search</button>
            </div>
        </>
    )
}

export default Search;