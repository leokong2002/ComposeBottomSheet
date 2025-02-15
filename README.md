# ComposeBottomSheet
This is the example of Compose Bottomsheet combining the usage of `ModalBottomSheet` and unstyled `BottomSheet` with MVVM.

<img width="534" alt="image" src="https://github.com/user-attachments/assets/0d82fb01-8ddc-4bab-aff5-926a443a8315" />

There are three buttons in on them main screen, clicking on each of them opens a bottom sheet

----

<img width="534" alt="image" src="https://github.com/user-attachments/assets/66cb8392-290b-4b11-a655-a16db677fa73" />

Clicking on the top button opens a Modal Bottom Sheet which wrap the context

----

<img width="534" alt="image" src="https://github.com/user-attachments/assets/e5460a32-fe2d-44a8-a184-40898e3385e0" />

Clicking on the middle button opens a Model Bottom Sheet which can be swpiped up to be a full height bottom sheet

----

<img width="534" alt="image" src="https://github.com/user-attachments/assets/33144786-7502-4a0d-9b2f-27e31ea6b19f" />

Clicking on the bottom button opens a unstyled Bottom Sheet without a drag handle, clicking on button is the only way to hide it. Replicated the `focus` effect by adding a canvas in the main screen, clicking on the bottom button triggers the visibility of the canvas as well
