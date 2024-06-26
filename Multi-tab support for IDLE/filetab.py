""" IDLE filetab view. """
from tkinter.ttk import Notebook, Style, Button 
import tkinter as tk

class FileTabView: 
    """Creates a file tab view in IDLE editor to see opened files."""
    def __init__(self, flist):
        """Initializes tab view with the FileList as the main tracker."""
        self.flist = flist
        # Keeps track of all the open EditorWindows.
        self.editwins = []
        # Keeps track of all the existing file buttons so that when a new file is opened
        # the existing buttons are added to the new view.
        self.opened_files_dict = {}
        self.opened_files_arr = []

    def add_editwin(self, editwin): 
        """Makes the tab view visible in the editor."""
        # Create a style
        self.style = Style(editwin.top)
        # Import available themes
        available_themes = self.style.theme_names()
        # Set a theme
        self.style.theme_use(available_themes[0])  # Change the index to switch themes
        self.style.configure('TNotebook.Tab', background='gray')
        if not hasattr(self, 'tab_control'):
            editwin.tab_control = Notebook(editwin.top)
            editwin.tab_control.pack(side=tk.LEFT, fill=tk.BOTH, expand=True)
        # Add these lines to create a frame for the text widget and add it to the tab
        editwin.text_frame = text_frame = tk.Frame(editwin.tab_control)
        editwin.tab_control.add(text_frame, text='Open Files')
        self.editwins.append(editwin) 
        if (len(self.editwins)>1): 
            self.editwins[0].close() 
            self.editwins.pop(0) 
        for path in self.flist.dict.keys(): 
            if (not (path in self.opened_files_dict.keys())): 
                self.opened_files_dict[path.split('/')[-1]] = path
            if (not (path.split('/')[-1] in self.opened_files_arr)):
              self.opened_files_arr.append(path.split('/')[-1])
        # Add existing buttons 
        for but in self.opened_files_dict:
            b = Button(editwin.text_frame, text=but, 
                       command=lambda but=but: self.open_file(but)) 
            b.pack()
            close_button = Button(editwin.text_frame, text="X", 
                                  command=lambda but=but: self.close_file(but))
            close_button.pack()

    def open_file(self, name): 
      self.flist.open(self.opened_files_dict[name])

    def close_file(self, name):
      """
      Closes the corresponding file in the file view tab.
      If there are more files open, it opens the next file in the list.
      """
      del self.opened_files_dict[name]
      self.editwins[0].close()
      if len(self.opened_files_dict) > 0:
        file_index = self.opened_files_arr.index(name)
        if file_index == (len(self.opened_files_arr) - 1):
          next_file = self.opened_files_arr[file_index - 1]
        else:
          next_file = self.opened_files_arr[file_index + 1]
        self.flist.open(self.opened_files_dict[next_file])
      self.opened_files_arr.remove(name)
    
if __name__ == "__main__":
    import unittest
    unittest.main('idlelib.idle_test.filetab', verbosity=2, exit=False)