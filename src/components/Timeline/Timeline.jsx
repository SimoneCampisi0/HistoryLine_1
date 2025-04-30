import './Timeline.css';

function Timeline() {

    //TODO: ricevere eventList da Search.jsx
    const eventList = [
        {
            "eventName": "Nascita di Attila",
            "eventDescription": "Attila nacque intorno al 395, figlio di Munzuco, e perse il padre da bambino, imparando a andare a cavallo e combattere in tenera età.",
            "eventDate": "0394-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Imposizione del tributo romano agli Unni",
            "eventDescription": "Roma concluse un trattato di pace con Rua, zio di Attila, imponendo un tributo annuale di 160 chili d'oro e lo schiavismo di alcuni ostaggi, tra cui Attila stesso, che imparò il latino.",
            "eventDate": "0399-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Ascesa di Attila come re degli Unni",
            "eventDescription": "Dopo la morte di Rua nel 434, Attila divenne re degli Unni in condivisione con il fratello Bleda, inizializzando il suo regno e le sue campagne militari.",
            "eventDate": "0433-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Accordo di Margus e campagne del 441-442",
            "eventDescription": "Nel 439-442 Attila e Bleda stipularono accordi con Roma, riconsegnarono prigionieri, aumentarono il tributo e condussero campagne militari nei Balcani, saccheggiando Naissus e altre città.",
            "eventDate": "0440-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Morte di Bleda e ascesa di Attila come re unico",
            "eventDescription": "Attila divenne re degli Unni da solo intorno al 445, dopo la morte del fratello Bleda, consolidando il suo potere e pianificando nuove invasioni.",
            "eventDate": "0444-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Campagne balcaniche del 447",
            "eventDescription": "Nel 447 Attila condusse campagne devastando i Balcani, saccheggiando Aquileia, Pavia e Milano, e affrontando le forze romane e visigote nei Campi Catalaunici, dove la battaglia si concluse con una ritirata romana.",
            "eventDate": "0446-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Invasione dell'Italia e saccheggio di Milano",
            "eventDescription": "Nel 452 Attila invase l'Italia, assediò Aquileia e Pavia, e saccheggiò Milano, ma si ritirò dopo l'intervento di Papa Leone I e le trattative di pace.",
            "eventDate": "0451-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Incontro con Papa Leone I a Governolo",
            "eventDescription": "Attila incontrò Papa Leone I nel 452 presso Governolo, dove secondo la leggenda fu persuaso a ritirarsi grazie all'intervento divino e alla presenza delle sacre figure.",
            "eventDate": "0451-12-31T23:00:00.000+00:00"
        },
        {
            "eventName": "Morte di Attila",
            "eventDescription": "Attila morì il 16 marzo 453, probabilmente soffocato da un'emorragia dopo un banchetto, in circostanze avvolte nel mistero, e fu sepolto in segreto in un sarcofago di oro, argento e ferro.",
            "eventDate": "0453-03-15T23:00:00.000+00:00"
        }
    ];

    return (
        <>
            <section className="py-5">
                <ul className="timeline">
                    {eventList.map((item, index) => (
                        <li className="timeline-item mb-5" key={index}>
                            <h5 className="fw-bold text-white">{item.eventName}</h5>
                            <p className="text-muted mb-2 fw-bold text-white">{item.eventDate}</p>
                            <p className="text-muted">
                                {item.eventDescription}
                            </p>
                        </li>
                    ))}
                </ul>
            </section>
        </>
    )
}

export default Timeline;