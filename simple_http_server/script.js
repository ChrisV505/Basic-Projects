const facts = [
  "Honey never spoils.",
  "A group of flamingos is called a flamboyance.",
  "Bananas are berries, but strawberries aren't.",
  "Wombat poop is cube-shaped.",
  "Octopuses have three hearts.",
  "There are more stars in the universe than grains of sand on Earth.",
  "Sharks existed before trees.",
  "The Eiffel Tower can grow over 6 inches in the summer.",
];

const factButton = document.getElementById("factButton");
const factDisplay = document.getElementById("factDisplay");

factButton.addEventListener("click", () => {
    const randomIndex = Math.floor(Math.random() * facts.length);
    factDisplay.textContent = facts[randomIndex];
});
