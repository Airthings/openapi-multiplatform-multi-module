import SwiftUI
import shared

struct ContentView: View {
    @State private var text = "Loading.. ⏱"

	var body: some View {
        Text(text).onAppear {
            Greeting().greetPet { greeting, _ in
                text = greeting ?? "Something went wrong. 💩"
            }
        }
	}
}

struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
		ContentView()
	}
}
