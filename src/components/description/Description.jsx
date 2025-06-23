function Description({ resultEvents }) {


    return (
        <>
            <div className="text-white row">
                <div className="col-auto">Photo</div>
                <div className="col">
                    <div>{resultEvents.characterDescription.characterName}</div>
                    <div>
                        {resultEvents.characterDescription.description}
                    </div>
                </div>
            </div>
        </>
    )
}
export default Description;