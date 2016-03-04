var feed = new Instafeed({
    get: 'tagged',
    tagName: 'jirkovocoffee',
    clientId: '1ae376b2798f45169b0932668a317d60',
    resolution: "low_resolution",
    template: '<a href="{{link}}"><img class="instagram" src="{{image}}" /></a>'
});
feed.run();