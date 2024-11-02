import random as rd
import time as tm


class PokerGame:
    
    translation_dictionary_rank = {
        #Woah this is a nifty little trick, thanks ChatGPT
        i: {1: '2nd', 2: '3rd'}.get(i, str(i+1) + 'th') if i < 10 else {10: 'Jack', 11: 'Queen', 12: 'King', 13: 'Ace'}[i]
        for i in range(1, 14)
        }
    
    translation_dictionary_suit = {
        1: 'Clubs', 2: 'Diamonds', 3: 'Spades', 4: 'Hearts'
        }
    
    def __init__(self, players = 4):
        self.cards = []
        for i in range(1, 5):
            for j in range(1, 14):
                self.cards.append((i, j))
        
        self.hands = {player: set() for player in range(1, players + 1)}
        
        self.global_cards = []
        
        self.display_state = -1
    
    def get_hand(self, player_num = 0):
        #If 0 return all hands, else return that players hand
        if player_num == 0:
            return self.hands
        elif 0 < player_num <= len(self.hands):
            return self.hands[player_num]
        else:
            raise ValueError("Invalid getter call, likely less total players than called")
    
    def get_deck(self):
        return self.cards
        
    def get_global_cards(self):
        return self.global_cards
        
            
    def shuffle_deck(self):
        rd.shuffle(self.cards)
        
        ''' NOTE: code here just for example, turns out python has an inbuilt 
        fisher-yates shuffle, time complexity O(n)
            
        shuffled_cards = []
        #Learning point, without .copy you are just creating reference
        current_cards = self.cards.copy()
            #Apperently while current_cards: is sufficient, empty list, 'falsity'
            
        while len(current_cards) > 1:
            index = rd.randint(0, len(current_cards) - 1)
            shuffled_cards.append(current_cards.pop(index))
            
        self.cards = shuffled_cards
        '''
    
    def deal_hand(self, num_cards=2):
        for player in self.hands:
            for i in range(num_cards):
                card = self.cards.pop()
                self.hands[player].add(card)

    def deal_global_cards(self):
        for i in range(5):
            card = self.cards.pop()
            self.global_cards.append(card)
    
    def translate_card(card_tuple):
        suit = PokerGame.translation_dictionary_suit.get(card_tuple[0], "Suit error")
        rank = PokerGame.translation_dictionary_rank.get(card_tuple[1], "Rank error")
        return f"{rank} of {suit}"
    
    def display_hand(self, player_num = 0):
           
        if player_num != 0:
            player_hand = list(self.get_hand(player_num))
            
            if len(player_hand) == 2:
                print("Player {} holds: \nThe {} and the {}.\n".format(
                    player_num, 
                    PokerGame.translate_card(player_hand[0]), 
                    PokerGame.translate_card(player_hand[1])))
            else:
                print(f"Player {player_num} has an invalid number of cards, have you dealt.")
       
        else:
            for i in range(1, 5):
                self.display_hand(i)
    
    def display_global(self, max_state = 0):
             
        if self.display_state < 0 and max_state == 0:
            print("There are no cards on the table")
        
        if self.display_state < 1 and max_state >= 1:
            print("\nThe cards on the table are:")
            
            for i in range(3):
                print("The {}".format(PokerGame.translate_card(self.global_cards[i])))
        
        if self.display_state < 2 and max_state >= 2:
            print("The {}".format(PokerGame.translate_card(self.global_cards[3])))
        
        if self.display_state < 3 and max_state >= 3:
            print("The {}".format(PokerGame.translate_card(self.global_cards[4])))
        
        self.display_state = max_state
    
    def hand_value(self, player_num):
        has_pair = False
        has_double_pair = False
        has_three_of_a_kind = False
        has_straight = False
        has_four_of_a_kind = False
        has_four_of_a_kind = False
        
    
    def play(self, delay = 0):
        self.shuffle_deck()
        self.deal_hand()
        self.display_hand()
        self.deal_global_cards()
        self.display_global(0)
        self.display_global(1)
        self.display_global(2)
        self.display_global(3)
    

# Example usage
game = PokerGame(players=4)   
game.play(delay = 2)



