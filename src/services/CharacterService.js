export async function getSuggestCharacters(object) {
    let url = import.meta.env.VITE_API_DEVELOPMENT
    const response = await fetch(url + `/api/character`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        }, body: JSON.stringify(object),
    })

    if(!response.ok) {
        throw new Error("Errore chiamata getSuggestCharacters")
    }
    return response.json()
}