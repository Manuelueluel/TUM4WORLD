var randomQuotes = function(id) {

    var randomizeArray = function( arr ) {
        var randomized = arr.sort(function() {
            return Math.random() - 0.5;
        });
        return randomized;
    };

    var _q = [
        'Se qualcosa non ti piace, cambiala. Se non puoi cambiarla, cambia il tuo atteggiamento.\n',
        'Se vuoi qualcosa che non hai mai avuto, devi fare qualcosa che non hai mai fatto.\n',
        'Il mio scopo nella vita? Fare solo cose positive.\n',
        'Non è mai troppo tardi per essere ciò che avresti voluto essere\n',
        'Credi in te stesso quando nessun altro lo fa. Ciò ti rende all’istante un vincitore.\n',
        'Non puoi mettere limiti a niente. Più sogni, più andrai lontano.\n',
        'Non importa chi tu sia, o da dove tu venga. La capacità di trionfare inizia con te. Sempre.'
    ];
    _q = randomizeArray(_q);
    var _current = 0;
    var _last = _q.length;
    var _element = document.getElementById(id);

    var _shuffle = function() {
        // Riporta il nuovo ordine shuffle nella console firebug (se presente)
        if (typeof(console) === 'object') console.log(_q);
    };

    return {
        rotate: function(o) {
            _shuffle();
            (function() {
                _element.innerHTML = _q[_current++];
                if (_current === _last) {
                    _current = 0;
                    if (o.reshuffle) _shuffle();
                };
                setTimeout(arguments.callee, o.pause);
            })();
        }
    };

};

window.onload = function() {
    randomQuotes('fraseMotivazionale').rotate({
        'pause': 20000,
        'reshuffle': true
    });
};