let HashMap = function() {
    const map = {};
    return {
        put: function(k, v) {
          map[k] = v;
        },
        keys: function() {
            return Object.keys(map);
        },
        contains: function(k) {
            return this.keys().includes(k);
        },
        get: function(k) {
            return map[k];
        },
        clear: function() {
           for (const item in map) {
            delete map[item];
           }
        }
    };
};